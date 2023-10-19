import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IFrog } from '@/shared/model/frog.model';

import FrogService from './frog.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Frog extends Vue {
  @Inject('frogService') private frogService: () => FrogService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public frogs: IFrog[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllFrogs();
  }

  public clear(): void {
    this.retrieveAllFrogs();
  }

  public retrieveAllFrogs(): void {
    this.isFetching = true;
    this.frogService()
      .retrieve()
      .then(
        res => {
          this.frogs = res.data;
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

  public prepareRemove(instance: IFrog): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeFrog(): void {
    this.frogService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('xmplVueSpringwiseApp.frog.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllFrogs();
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
