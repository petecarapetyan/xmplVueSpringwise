import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IScoreType } from '@/shared/model/score-type.model';

import ScoreTypeService from './score-type.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class ScoreType extends Vue {
  @Inject('scoreTypeService') private scoreTypeService: () => ScoreTypeService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public scoreTypes: IScoreType[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllScoreTypes();
  }

  public clear(): void {
    this.retrieveAllScoreTypes();
  }

  public retrieveAllScoreTypes(): void {
    this.isFetching = true;
    this.scoreTypeService()
      .retrieve()
      .then(
        res => {
          this.scoreTypes = res.data;
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

  public prepareRemove(instance: IScoreType): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeScoreType(): void {
    this.scoreTypeService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('xmplVueSpringwiseApp.scoreType.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllScoreTypes();
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
