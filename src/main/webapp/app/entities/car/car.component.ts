import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICar } from '@/shared/model/car.model';

import CarService from './car.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Car extends Vue {
  @Inject('carService') private carService: () => CarService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public cars: ICar[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllCars();
  }

  public clear(): void {
    this.retrieveAllCars();
  }

  public retrieveAllCars(): void {
    this.isFetching = true;
    this.carService()
      .retrieve()
      .then(
        res => {
          this.cars = res.data;
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

  public prepareRemove(instance: ICar): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeCar(): void {
    this.carService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('xmplVueSpringwiseApp.car.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllCars();
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
