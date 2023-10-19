<template>
  <div>
    <h2 id="page-heading" data-cy="TruckHeading">
      <span v-text="$t('xmplVueSpringwiseApp.truck.home.title')" id="truck-heading">Trucks</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('xmplVueSpringwiseApp.truck.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'TruckCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-truck"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('xmplVueSpringwiseApp.truck.home.createLabel')"> Create a new Truck </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && trucks && trucks.length === 0">
      <span v-text="$t('xmplVueSpringwiseApp.truck.home.notFound')">No trucks found</span>
    </div>
    <div class="table-responsive" v-if="trucks && trucks.length > 0">
      <table class="table table-striped" aria-describedby="trucks">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('xmplVueSpringwiseApp.truck.modelName')">Model Name</span></th>
            <th scope="row"><span v-text="$t('xmplVueSpringwiseApp.truck.make')">Make</span></th>
            <th scope="row"><span v-text="$t('xmplVueSpringwiseApp.truck.motorSize')">Motor Size</span></th>
            <th scope="row"><span v-text="$t('xmplVueSpringwiseApp.truck.color')">Color</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="truck in trucks" :key="truck.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'TruckView', params: { truckId: truck.id } }">{{ truck.id }}</router-link>
            </td>
            <td>{{ truck.modelName }}</td>
            <td>{{ truck.make }}</td>
            <td>{{ truck.motorSize }}</td>
            <td>{{ truck.color }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'TruckView', params: { truckId: truck.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'TruckEdit', params: { truckId: truck.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(truck)"
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
        ><span id="xmplVueSpringwiseApp.truck.delete.question" data-cy="truckDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-truck-heading" v-text="$t('xmplVueSpringwiseApp.truck.delete.question', { id: removeId })">
          Are you sure you want to delete this Truck?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-truck"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeTruck()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./truck.component.ts"></script>
