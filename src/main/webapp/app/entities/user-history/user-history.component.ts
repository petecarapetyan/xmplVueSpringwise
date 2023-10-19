import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IUserHistory } from '@/shared/model/user-history.model';

import UserHistoryService from './user-history.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class UserHistory extends Vue {
  @Inject('userHistoryService') private userHistoryService: () => UserHistoryService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public userHistories: IUserHistory[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllUserHistorys();
  }

  public clear(): void {
    this.retrieveAllUserHistorys();
  }

  public retrieveAllUserHistorys(): void {
    this.isFetching = true;
    this.userHistoryService()
      .retrieve()
      .then(
        res => {
          this.userHistories = res.data;
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

  public prepareRemove(instance: IUserHistory): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeUserHistory(): void {
    this.userHistoryService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('xmplVueSpringwiseApp.userHistory.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllUserHistorys();
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
