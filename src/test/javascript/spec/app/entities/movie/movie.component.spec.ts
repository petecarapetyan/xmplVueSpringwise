/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import MovieComponent from '@/entities/movie/movie.vue';
import MovieClass from '@/entities/movie/movie.component';
import MovieService from '@/entities/movie/movie.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('Movie Management Component', () => {
    let wrapper: Wrapper<MovieClass>;
    let comp: MovieClass;
    let movieServiceStub: SinonStubbedInstance<MovieService>;

    beforeEach(() => {
      movieServiceStub = sinon.createStubInstance<MovieService>(MovieService);
      movieServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<MovieClass>(MovieComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          movieService: () => movieServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      movieServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllMovies();
      await comp.$nextTick();

      // THEN
      expect(movieServiceStub.retrieve.called).toBeTruthy();
      expect(comp.movies[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      movieServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(movieServiceStub.retrieve.callCount).toEqual(1);

      comp.removeMovie();
      await comp.$nextTick();

      // THEN
      expect(movieServiceStub.delete.called).toBeTruthy();
      expect(movieServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
