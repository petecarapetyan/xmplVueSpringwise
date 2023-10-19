import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ISpringProject } from '@/shared/model/spring-project.model';

import SpringProjectService from './spring-project.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class SpringProject extends Vue {
  @Inject('springProjectService') private springProjectService: () => SpringProjectService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public springProjects: ISpringProject[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllSpringProjects();
  }

  public clear(): void {
    this.retrieveAllSpringProjects();
  }

  public retrieveAllSpringProjects(): void {
    this.isFetching = true;
    this.springProjectService()
      .retrieve()
      .then(
        res => {
          this.springProjects = res.data;
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

  public prepareRemove(instance: ISpringProject): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeSpringProject(): void {
    this.springProjectService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('xmplVueSpringwiseApp.springProject.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllSpringProjects();
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
