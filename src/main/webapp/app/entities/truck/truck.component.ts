import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ITruck } from '@/shared/model/truck.model';

import TruckService from './truck.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Truck extends Vue {
  @Inject('truckService') private truckService: () => TruckService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public trucks: ITruck[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllTrucks();
  }

  public clear(): void {
    this.retrieveAllTrucks();
  }

  public retrieveAllTrucks(): void {
    this.isFetching = true;
    this.truckService()
      .retrieve()
      .then(
        res => {
          this.trucks = res.data;
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

  public prepareRemove(instance: ITruck): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeTruck(): void {
    this.truckService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('xmplVueSpringwiseApp.truck.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllTrucks();
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
