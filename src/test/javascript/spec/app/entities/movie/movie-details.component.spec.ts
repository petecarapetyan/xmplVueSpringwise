/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import MovieDetailComponent from '@/entities/movie/movie-details.vue';
import MovieClass from '@/entities/movie/movie-details.component';
import MovieService from '@/entities/movie/movie.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Movie Management Detail Component', () => {
    let wrapper: Wrapper<MovieClass>;
    let comp: MovieClass;
    let movieServiceStub: SinonStubbedInstance<MovieService>;

    beforeEach(() => {
      movieServiceStub = sinon.createStubInstance<MovieService>(MovieService);

      wrapper = shallowMount<MovieClass>(MovieDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { movieService: () => movieServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundMovie = { id: 123 };
        movieServiceStub.find.resolves(foundMovie);

        // WHEN
        comp.retrieveMovie(123);
        await comp.$nextTick();

        // THEN
        expect(comp.movie).toBe(foundMovie);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundMovie = { id: 123 };
        movieServiceStub.find.resolves(foundMovie);

        // WHEN
        comp.beforeRouteEnter({ params: { movieId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.movie).toBe(foundMovie);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
