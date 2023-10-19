import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import { ICodingCategory, CodingCategory } from '@/shared/model/coding-category.model';
import CodingCategoryService from './coding-category.service';

const validations: any = {
  codingCategory: {
    name: {},
  },
};

@Component({
  validations,
})
export default class CodingCategoryUpdate extends Vue {
  @Inject('codingCategoryService') private codingCategoryService: () => CodingCategoryService;
  @Inject('alertService') private alertService: () => AlertService;

  public codingCategory: ICodingCategory = new CodingCategory();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.codingCategoryId) {
        vm.retrieveCodingCategory(to.params.codingCategoryId);
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
    if (this.codingCategory.id) {
      this.codingCategoryService()
        .update(this.codingCategory)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('xmplVueSpringwiseApp.codingCategory.updated', { param: param.id });
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
      this.codingCategoryService()
        .create(this.codingCategory)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('xmplVueSpringwiseApp.codingCategory.created', { param: param.id });
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

  public retrieveCodingCategory(codingCategoryId): void {
    this.codingCategoryService()
      .find(codingCategoryId)
      .then(res => {
        this.codingCategory = res;
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
