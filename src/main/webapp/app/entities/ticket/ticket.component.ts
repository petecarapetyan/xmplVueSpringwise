import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ITicket } from '@/shared/model/ticket.model';

import TicketService from './ticket.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Ticket extends Vue {
  @Inject('ticketService') private ticketService: () => TicketService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public tickets: ITicket[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllTickets();
  }

  public clear(): void {
    this.retrieveAllTickets();
  }

  public retrieveAllTickets(): void {
    this.isFetching = true;
    this.ticketService()
      .retrieve()
      .then(
        res => {
          this.tickets = res.data;
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

  public prepareRemove(instance: ITicket): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeTicket(): void {
    this.ticketService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('xmplVueSpringwiseApp.ticket.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllTickets();
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
