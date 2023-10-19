<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="xmplVueSpringwiseApp.truck.home.createOrEditLabel"
          data-cy="TruckCreateUpdateHeading"
          v-text="$t('xmplVueSpringwiseApp.truck.home.createOrEditLabel')"
        >
          Create or edit a Truck
        </h2>
        <div>
          <div class="form-group" v-if="truck.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="truck.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('xmplVueSpringwiseApp.truck.modelName')" for="truck-modelName">Model Name</label>
            <input
              type="text"
              class="form-control"
              name="modelName"
              id="truck-modelName"
              data-cy="modelName"
              :class="{ valid: !$v.truck.modelName.$invalid, invalid: $v.truck.modelName.$invalid }"
              v-model="$v.truck.modelName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('xmplVueSpringwiseApp.truck.make')" for="truck-make">Make</label>
            <input
              type="text"
              class="form-control"
              name="make"
              id="truck-make"
              data-cy="make"
              :class="{ valid: !$v.truck.make.$invalid, invalid: $v.truck.make.$invalid }"
              v-model="$v.truck.make.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('xmplVueSpringwiseApp.truck.motorSize')" for="truck-motorSize">Motor Size</label>
            <input
              type="number"
              class="form-control"
              name="motorSize"
              id="truck-motorSize"
              data-cy="motorSize"
              :class="{ valid: !$v.truck.motorSize.$invalid, invalid: $v.truck.motorSize.$invalid }"
              v-model.number="$v.truck.motorSize.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('xmplVueSpringwiseApp.truck.color')" for="truck-color">Color</label>
            <input
              type="text"
              class="form-control"
              name="color"
              id="truck-color"
              data-cy="color"
              :class="{ valid: !$v.truck.color.$invalid, invalid: $v.truck.color.$invalid }"
              v-model="$v.truck.color.$model"
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
            :disabled="$v.truck.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./truck-update.component.ts"></script>
