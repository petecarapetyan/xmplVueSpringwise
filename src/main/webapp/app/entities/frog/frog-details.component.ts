import { Component, Vue, Inject } from 'vue-property-decorator';

import { IFrog } from '@/shared/model/frog.model';
import FrogService from './frog.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class FrogDetails extends Vue {
  @Inject('frogService') private frogService: () => FrogService;
  @Inject('alertService') private alertService: () => AlertService;

  public frog: IFrog = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.frogId) {
        vm.retrieveFrog(to.params.frogId);
      }
    });
  }

  public retrieveFrog(frogId) {
    this.frogService()
      .find(frogId)
      .then(res => {
        this.frog = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
