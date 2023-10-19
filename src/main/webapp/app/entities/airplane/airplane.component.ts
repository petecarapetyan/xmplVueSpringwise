import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IAirplane } from '@/shared/model/airplane.model';

import AirplaneService from './airplane.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Airplane extends Vue {
  @Inject('airplaneService') private airplaneService: () => AirplaneService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public airplanes: IAirplane[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllAirplanes();
  }

  public clear(): void {
    this.retrieveAllAirplanes();
  }

  public retrieveAllAirplanes(): void {
    this.isFetching = true;
    this.airplaneService()
      .retrieve()
      .then(
        res => {
          this.airplanes = res.data;
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

  public prepareRemove(instance: IAirplane): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeAirplane(): void {
    this.airplaneService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('xmplVueSpringwiseApp.airplane.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllAirplanes();
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
