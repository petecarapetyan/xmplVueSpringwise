import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import { ISpringProject, SpringProject } from '@/shared/model/spring-project.model';
import SpringProjectService from './spring-project.service';

const validations: any = {
  springProject: {
    title: {},
    description: {},
    imagePath: {},
    url: {},
  },
};

@Component({
  validations,
})
export default class SpringProjectUpdate extends Vue {
  @Inject('springProjectService') private springProjectService: () => SpringProjectService;
  @Inject('alertService') private alertService: () => AlertService;

  public springProject: ISpringProject = new SpringProject();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.springProjectId) {
        vm.retrieveSpringProject(to.params.springProjectId);
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
    if (this.springProject.id) {
      this.springProjectService()
        .update(this.springProject)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('xmplVueSpringwiseApp.springProject.updated', { param: param.id });
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
      this.springProjectService()
        .create(this.springProject)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('xmplVueSpringwiseApp.springProject.created', { param: param.id });
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

  public retrieveSpringProject(springProjectId): void {
    this.springProjectService()
      .find(springProjectId)
      .then(res => {
        this.springProject = res;
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
