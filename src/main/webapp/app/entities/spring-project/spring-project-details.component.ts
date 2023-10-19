import { Component, Vue, Inject } from 'vue-property-decorator';

import { ISpringProject } from '@/shared/model/spring-project.model';
import SpringProjectService from './spring-project.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class SpringProjectDetails extends Vue {
  @Inject('springProjectService') private springProjectService: () => SpringProjectService;
  @Inject('alertService') private alertService: () => AlertService;

  public springProject: ISpringProject = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.springProjectId) {
        vm.retrieveSpringProject(to.params.springProjectId);
      }
    });
  }

  public retrieveSpringProject(springProjectId) {
    this.springProjectService()
      .find(springProjectId)
      .then(res => {
        this.springProject = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
