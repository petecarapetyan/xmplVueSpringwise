<template>
  <div>
    <h2 id="page-heading" data-cy="TicketHeading">
      <span v-text="$t('xmplVueSpringwiseApp.ticket.home.title')" id="ticket-heading">Tickets</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('xmplVueSpringwiseApp.ticket.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'TicketCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-ticket"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('xmplVueSpringwiseApp.ticket.home.createLabel')"> Create a new Ticket </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && tickets && tickets.length === 0">
      <span v-text="$t('xmplVueSpringwiseApp.ticket.home.notFound')">No tickets found</span>
    </div>
    <div class="table-responsive" v-if="tickets && tickets.length > 0">
      <table class="table table-striped" aria-describedby="tickets">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('xmplVueSpringwiseApp.ticket.issue')">Issue</span></th>
            <th scope="row"><span v-text="$t('xmplVueSpringwiseApp.ticket.timeStamp')">Time Stamp</span></th>
            <th scope="row"><span v-text="$t('xmplVueSpringwiseApp.ticket.student')">Student</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="ticket in tickets" :key="ticket.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'TicketView', params: { ticketId: ticket.id } }">{{ ticket.id }}</router-link>
            </td>
            <td>{{ ticket.issue }}</td>
            <td>{{ ticket.timeStamp ? $d(Date.parse(ticket.timeStamp), 'short') : '' }}</td>
            <td>
              <div v-if="ticket.student">
                <router-link :to="{ name: 'StudentView', params: { studentId: ticket.student.id } }">{{ ticket.student.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'TicketView', params: { ticketId: ticket.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'TicketEdit', params: { ticketId: ticket.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(ticket)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="xmplVueSpringwiseApp.ticket.delete.question" data-cy="ticketDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-ticket-heading" v-text="$t('xmplVueSpringwiseApp.ticket.delete.question', { id: removeId })">
          Are you sure you want to delete this Ticket?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-ticket"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeTicket()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./ticket.component.ts"></script>
