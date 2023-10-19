/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import TicketComponent from '@/entities/ticket/ticket.vue';
import TicketClass from '@/entities/ticket/ticket.component';
import TicketService from '@/entities/ticket/ticket.service';
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
  describe('Ticket Management Component', () => {
    let wrapper: Wrapper<TicketClass>;
    let comp: TicketClass;
    let ticketServiceStub: SinonStubbedInstance<TicketService>;

    beforeEach(() => {
      ticketServiceStub = sinon.createStubInstance<TicketService>(TicketService);
      ticketServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<TicketClass>(TicketComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          ticketService: () => ticketServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      ticketServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllTickets();
      await comp.$nextTick();

      // THEN
      expect(ticketServiceStub.retrieve.called).toBeTruthy();
      expect(comp.tickets[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      ticketServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(ticketServiceStub.retrieve.callCount).toEqual(1);

      comp.removeTicket();
      await comp.$nextTick();

      // THEN
      expect(ticketServiceStub.delete.called).toBeTruthy();
      expect(ticketServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
