import { Component, Vue, Inject } from 'vue-property-decorator';

import { IScoreType } from '@/shared/model/score-type.model';
import ScoreTypeService from './score-type.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class ScoreTypeDetails extends Vue {
  @Inject('scoreTypeService') private scoreTypeService: () => ScoreTypeService;
  @Inject('alertService') private alertService: () => AlertService;

  public scoreType: IScoreType = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.scoreTypeId) {
        vm.retrieveScoreType(to.params.scoreTypeId);
      }
    });
  }

  public retrieveScoreType(scoreTypeId) {
    this.scoreTypeService()
      .find(scoreTypeId)
      .then(res => {
        this.scoreType = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
