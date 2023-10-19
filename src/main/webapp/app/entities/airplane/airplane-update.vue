<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="xmplVueSpringwiseApp.airplane.home.createOrEditLabel"
          data-cy="AirplaneCreateUpdateHeading"
          v-text="$t('xmplVueSpringwiseApp.airplane.home.createOrEditLabel')"
        >
          Create or edit a Airplane
        </h2>
        <div>
          <div class="form-group" v-if="airplane.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="airplane.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('xmplVueSpringwiseApp.airplane.model')" for="airplane-model">Model</label>
            <input
              type="text"
              class="form-control"
              name="model"
              id="airplane-model"
              data-cy="model"
              :class="{ valid: !$v.airplane.model.$invalid, invalid: $v.airplane.model.$invalid }"
              v-model="$v.airplane.model.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('xmplVueSpringwiseApp.airplane.make')" for="airplane-make">Make</label>
            <input
              type="text"
              class="form-control"
              name="make"
              id="airplane-make"
              data-cy="make"
              :class="{ valid: !$v.airplane.make.$invalid, invalid: $v.airplane.make.$invalid }"
              v-model="$v.airplane.make.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('xmplVueSpringwiseApp.airplane.color')" for="airplane-color">Color</label>
            <input
              type="text"
              class="form-control"
              name="color"
              id="airplane-color"
              data-cy="color"
              :class="{ valid: !$v.airplane.color.$invalid, invalid: $v.airplane.color.$invalid }"
              v-model="$v.airplane.color.$model"
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
            :disabled="$v.airplane.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./airplane-update.component.ts"></script>
