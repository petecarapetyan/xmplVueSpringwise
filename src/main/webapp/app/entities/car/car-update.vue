<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="xmplVueSpringwiseApp.car.home.createOrEditLabel"
          data-cy="CarCreateUpdateHeading"
          v-text="$t('xmplVueSpringwiseApp.car.home.createOrEditLabel')"
        >
          Create or edit a Car
        </h2>
        <div>
          <div class="form-group" v-if="car.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="car.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('xmplVueSpringwiseApp.car.motorSize')" for="car-motorSize">Motor Size</label>
            <input
              type="text"
              class="form-control"
              name="motorSize"
              id="car-motorSize"
              data-cy="motorSize"
              :class="{ valid: !$v.car.motorSize.$invalid, invalid: $v.car.motorSize.$invalid }"
              v-model="$v.car.motorSize.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('xmplVueSpringwiseApp.car.modelName')" for="car-modelName">Model Name</label>
            <input
              type="text"
              class="form-control"
              name="modelName"
              id="car-modelName"
              data-cy="modelName"
              :class="{ valid: !$v.car.modelName.$invalid, invalid: $v.car.modelName.$invalid }"
              v-model="$v.car.modelName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('xmplVueSpringwiseApp.car.wheelSize')" for="car-wheelSize">Wheel Size</label>
            <input
              type="text"
              class="form-control"
              name="wheelSize"
              id="car-wheelSize"
              data-cy="wheelSize"
              :class="{ valid: !$v.car.wheelSize.$invalid, invalid: $v.car.wheelSize.$invalid }"
              v-model="$v.car.wheelSize.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('xmplVueSpringwiseApp.car.transmission')" for="car-transmission"
              >Transmission</label
            >
            <input
              type="text"
              class="form-control"
              name="transmission"
              id="car-transmission"
              data-cy="transmission"
              :class="{ valid: !$v.car.transmission.$invalid, invalid: $v.car.transmission.$invalid }"
              v-model="$v.car.transmission.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('xmplVueSpringwiseApp.car.color')" for="car-color">Color</label>
            <input
              type="text"
              class="form-control"
              name="color"
              id="car-color"
              data-cy="color"
              :class="{ valid: !$v.car.color.$invalid, invalid: $v.car.color.$invalid }"
              v-model="$v.car.color.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('xmplVueSpringwiseApp.car.yearOf')" for="car-yearOf">Year Of</label>
            <input
              type="number"
              class="form-control"
              name="yearOf"
              id="car-yearOf"
              data-cy="yearOf"
              :class="{ valid: !$v.car.yearOf.$invalid, invalid: $v.car.yearOf.$invalid }"
              v-model.number="$v.car.yearOf.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('xmplVueSpringwiseApp.car.price')" for="car-price">Price</label>
            <input
              type="number"
              class="form-control"
              name="price"
              id="car-price"
              data-cy="price"
              :class="{ valid: !$v.car.price.$invalid, invalid: $v.car.price.$invalid }"
              v-model.number="$v.car.price.$model"
            />
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.car.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./car-update.component.ts"></script>
