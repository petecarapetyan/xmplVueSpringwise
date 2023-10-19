import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IDog } from '@/shared/model/dog.model';

import DogService from './dog.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Dog extends Vue {
  @Inject('dogService') private dogService: () => DogService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public dogs: IDog[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllDogs();
  }

  public clear(): void {
    this.retrieveAllDogs();
  }

  public retrieveAllDogs(): void {
    this.isFetching = true;
    this.dogService()
      .retrieve()
      .then(
        res => {
          this.dogs = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
          this.alertService().showHttpError(this, err.response);
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IDog): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeDog(): void {
    this.dogService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('xmplVueSpringwiseApp.dog.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllDogs();
        this.closeDialog();
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
