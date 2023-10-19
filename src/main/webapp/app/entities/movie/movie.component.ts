import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IMovie } from '@/shared/model/movie.model';

import MovieService from './movie.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Movie extends Vue {
  @Inject('movieService') private movieService: () => MovieService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public movies: IMovie[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllMovies();
  }

  public clear(): void {
    this.retrieveAllMovies();
  }

  public retrieveAllMovies(): void {
    this.isFetching = true;
    this.movieService()
      .retrieve()
      .then(
        res => {
          this.movies = res.data;
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

  public prepareRemove(instance: IMovie): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeMovie(): void {
    this.movieService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('xmplVueSpringwiseApp.movie.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllMovies();
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
