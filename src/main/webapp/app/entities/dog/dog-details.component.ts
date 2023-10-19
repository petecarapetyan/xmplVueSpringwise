import { Component, Vue, Inject } from 'vue-property-decorator';

import { IDog } from '@/shared/model/dog.model';
import DogService from './dog.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class DogDetails extends Vue {
  @Inject('dogService') private dogService: () => DogService;
  @Inject('alertService') private alertService: () => AlertService;

  public dog: IDog = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.dogId) {
        vm.retrieveDog(to.params.dogId);
      }
    });
  }

  public retrieveDog(dogId) {
    this.dogService()
      .find(dogId)
      .then(res => {
        this.dog = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
