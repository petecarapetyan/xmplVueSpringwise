import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import { IMovie, Movie } from '@/shared/model/movie.model';
import MovieService from './movie.service';

const validations: any = {
  movie: {
    name: {},
    yearOf: {},
    genre: {},
    rating: {},
  },
};

@Component({
  validations,
})
export default class MovieUpdate extends Vue {
  @Inject('movieService') private movieService: () => MovieService;
  @Inject('alertService') private alertService: () => AlertService;

  public movie: IMovie = new Movie();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.movieId) {
        vm.retrieveMovie(to.params.movieId);
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
    if (this.movie.id) {
      this.movieService()
        .update(this.movie)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('xmplVueSpringwiseApp.movie.updated', { param: param.id });
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
      this.movieService()
        .create(this.movie)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('xmplVueSpringwiseApp.movie.created', { param: param.id });
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

  public retrieveMovie(movieId): void {
    this.movieService()
      .find(movieId)
      .then(res => {
        this.movie = res;
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
