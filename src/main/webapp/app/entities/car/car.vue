<template>
  <div>
    <h2 id="page-heading" data-cy="CarHeading">
      <span v-text="$t('xmplVueSpringwiseApp.car.home.title')" id="car-heading">Cars</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('xmplVueSpringwiseApp.car.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'CarCreate' }" custom v-slot="{ navigate }">
          <button @click="navigate" id="jh-create-entity" data-cy="entityCreateButton" class="btn btn-primary jh-create-entity create-car">
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('xmplVueSpringwiseApp.car.home.createLabel')"> Create a new Car </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && cars && cars.length === 0">
      <span v-text="$t('xmplVueSpringwiseApp.car.home.notFound')">No cars found</span>
    </div>
    <div class="table-responsive" v-if="cars && cars.length > 0">
      <table class="table table-striped" aria-describedby="cars">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('xmplVueSpringwiseApp.car.motorSize')">Motor Size</span></th>
            <th scope="row"><span v-text="$t('xmplVueSpringwiseApp.car.modelName')">Model Name</span></th>
            <th scope="row"><span v-text="$t('xmplVueSpringwiseApp.car.wheelSize')">Wheel Size</span></th>
            <th scope="row"><span v-text="$t('xmplVueSpringwiseApp.car.transmission')">Transmission</span></th>
            <th scope="row"><span v-text="$t('xmplVueSpringwiseApp.car.color')">Color</span></th>
            <th scope="row"><span v-text="$t('xmplVueSpringwiseApp.car.yearOf')">Year Of</span></th>
            <th scope="row"><span v-text="$t('xmplVueSpringwiseApp.car.price')">Price</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="car in cars" :key="car.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'CarView', params: { carId: car.id } }">{{ car.id }}</router-link>
            </td>
            <td>{{ car.motorSize }}</td>
            <td>{{ car.modelName }}</td>
            <td>{{ car.wheelSize }}</td>
            <td>{{ car.transmission }}</td>
            <td>{{ car.color }}</td>
            <td>{{ car.yearOf }}</td>
            <td>{{ car.price }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'CarView', params: { carId: car.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'CarEdit', params: { carId: car.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(car)"
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
        ><span id="xmplVueSpringwiseApp.car.delete.question" data-cy="carDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-car-heading" v-text="$t('xmplVueSpringwiseApp.car.delete.question', { id: removeId })">
          Are you sure you want to delete this Car?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-car"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeCar()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./car.component.ts"></script>
