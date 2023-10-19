import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAirplane } from '@/shared/model/airplane.model';
import AirplaneService from './airplane.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class AirplaneDetails extends Vue {
  @Inject('airplaneService') private airplaneService: () => AirplaneService;
  @Inject('alertService') private alertService: () => AlertService;

  public airplane: IAirplane = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.airplaneId) {
        vm.retrieveAirplane(to.params.airplaneId);
      }
    });
  }

  public retrieveAirplane(airplaneId) {
    this.airplaneService()
      .find(airplaneId)
      .then(res => {
        this.airplane = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
