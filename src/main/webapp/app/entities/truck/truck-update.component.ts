import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import { ITruck, Truck } from '@/shared/model/truck.model';
import TruckService from './truck.service';

const validations: any = {
  truck: {
    modelName: {},
    make: {},
    motorSize: {},
    color: {},
  },
};

@Component({
  validations,
})
export default class TruckUpdate extends Vue {
  @Inject('truckService') private truckService: () => TruckService;
  @Inject('alertService') private alertService: () => AlertService;

  public truck: ITruck = new Truck();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.truckId) {
        vm.retrieveTruck(to.params.truckId);
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
    if (this.truck.id) {
      this.truckService()
        .update(this.truck)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('xmplVueSpringwiseApp.truck.updated', { param: param.id });
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
      this.truckService()
        .create(this.truck)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('xmplVueSpringwiseApp.truck.created', { param: param.id });
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

  public retrieveTruck(truckId): void {
    this.truckService()
      .find(truckId)
      .then(res => {
        this.truck = res;
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
