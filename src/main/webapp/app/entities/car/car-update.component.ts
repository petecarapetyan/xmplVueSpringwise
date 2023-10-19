import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import { ICar, Car } from '@/shared/model/car.model';
import CarService from './car.service';

const validations: any = {
  car: {
    motorSize: {},
    modelName: {},
    wheelSize: {},
    transmission: {},
    color: {},
    yearOf: {},
    price: {},
  },
};

@Component({
  validations,
})
export default class CarUpdate extends Vue {
  @Inject('carService') private carService: () => CarService;
  @Inject('alertService') private alertService: () => AlertService;

  public car: ICar = new Car();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.carId) {
        vm.retrieveCar(to.params.carId);
      }
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.car.id) {
      this.carService()
        .update(this.car)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('xmplVueSpringwiseApp.car.updated', { param: param.id });
          return (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.carService()
        .create(this.car)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('xmplVueSpringwiseApp.car.created', { param: param.id });
          (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public retrieveCar(carId): void {
    this.carService()
      .find(carId)
      .then(res => {
        this.car = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
