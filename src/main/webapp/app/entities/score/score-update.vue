<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="xmplVueSpringwiseApp.score.home.createOrEditLabel"
          data-cy="ScoreCreateUpdateHeading"
          v-text="$t('xmplVueSpringwiseApp.score.home.createOrEditLabel')"
        >
          Create or edit a Score
        </h2>
        <div>
          <div class="form-group" v-if="score.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="score.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('xmplVueSpringwiseApp.score.points')" for="score-points">Points</label>
            <input
              type="text"
              class="form-control"
              name="points"
              id="score-points"
              data-cy="points"
              :class="{ valid: !$v.score.points.$invalid, invalid: $v.score.points.$invalid }"
              v-model="$v.score.points.$model"
              required
            />
            <div v-if="$v.score.points.$anyDirty && $v.score.points.$invalid">
              <small class="form-text text-danger" v-if="!$v.score.points.required" v-text="$t('entity.validation.required')">
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
            :disabled="$v.score.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./score-update.component.ts"></script>
