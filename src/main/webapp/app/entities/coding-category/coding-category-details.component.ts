import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICodingCategory } from '@/shared/model/coding-category.model';
import CodingCategoryService from './coding-category.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class CodingCategoryDetails extends Vue {
  @Inject('codingCategoryService') private codingCategoryService: () => CodingCategoryService;
  @Inject('alertService') private alertService: () => AlertService;

  public codingCategory: ICodingCategory = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.codingCategoryId) {
        vm.retrieveCodingCategory(to.params.codingCategoryId);
      }
    });
  }

  public retrieveCodingCategory(codingCategoryId) {
    this.codingCategoryService()
      .find(codingCategoryId)
      .then(res => {
        this.codingCategory = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
