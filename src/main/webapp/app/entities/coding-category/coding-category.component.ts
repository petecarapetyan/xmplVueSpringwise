import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICodingCategory } from '@/shared/model/coding-category.model';

import CodingCategoryService from './coding-category.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class CodingCategory extends Vue {
  @Inject('codingCategoryService') private codingCategoryService: () => CodingCategoryService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public codingCategories: ICodingCategory[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllCodingCategorys();
  }

  public clear(): void {
    this.retrieveAllCodingCategorys();
  }

  public retrieveAllCodingCategorys(): void {
    this.isFetching = true;
    this.codingCategoryService()
      .retrieve()
      .then(
        res => {
          this.codingCategories = res.data;
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

  public prepareRemove(instance: ICodingCategory): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeCodingCategory(): void {
    this.codingCategoryService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('xmplVueSpringwiseApp.codingCategory.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllCodingCategorys();
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
