import { Component, Vue, Inject } from 'vue-property-decorator';

import { IMovie } from '@/shared/model/movie.model';
import MovieService from './movie.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class MovieDetails extends Vue {
  @Inject('movieService') private movieService: () => MovieService;
  @Inject('alertService') private alertService: () => AlertService;

  public movie: IMovie = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.movieId) {
        vm.retrieveMovie(to.params.movieId);
      }
    });
  }

  public retrieveMovie(movieId) {
    this.movieService()
      .find(movieId)
      .then(res => {
        this.movie = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
