<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="xmplVueSpringwiseApp.scoreType.home.createOrEditLabel"
          data-cy="ScoreTypeCreateUpdateHeading"
          v-text="$t('xmplVueSpringwiseApp.scoreType.home.createOrEditLabel')"
        >
          Create or edit a ScoreType
        </h2>
        <div>
          <div class="form-group" v-if="scoreType.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="scoreType.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('xmplVueSpringwiseApp.scoreType.name')" for="score-type-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="score-type-name"
              data-cy="name"
              :class="{ valid: !$v.scoreType.name.$invalid, invalid: $v.scoreType.name.$invalid }"
              v-model="$v.scoreType.name.$model"
              required
            />
            <div v-if="$v.scoreType.name.$anyDirty && $v.scoreType.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.scoreType.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
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
            :disabled="$v.scoreType.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./score-type-update.component.ts"></script>
