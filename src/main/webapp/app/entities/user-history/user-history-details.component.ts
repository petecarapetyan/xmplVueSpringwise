import { Component, Vue, Inject } from 'vue-property-decorator';

import { IUserHistory } from '@/shared/model/user-history.model';
import UserHistoryService from './user-history.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class UserHistoryDetails extends Vue {
  @Inject('userHistoryService') private userHistoryService: () => UserHistoryService;
  @Inject('alertService') private alertService: () => AlertService;

  public userHistory: IUserHistory = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.userHistoryId) {
        vm.retrieveUserHistory(to.params.userHistoryId);
      }
    });
  }

  public retrieveUserHistory(userHistoryId) {
    this.userHistoryService()
      .find(userHistoryId)
      .then(res => {
        this.userHistory = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
