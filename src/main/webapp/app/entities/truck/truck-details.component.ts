import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITruck } from '@/shared/model/truck.model';
import TruckService from './truck.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class TruckDetails extends Vue {
  @Inject('truckService') private truckService: () => TruckService;
  @Inject('alertService') private alertService: () => AlertService;

  public truck: ITruck = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.truckId) {
        vm.retrieveTruck(to.params.truckId);
      }
    });
  }

  public retrieveTruck(truckId) {
    this.truckService()
      .find(truckId)
      .then(res => {
        this.truck = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
