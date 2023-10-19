<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="xmplVueSpringwiseApp.userHistory.home.createOrEditLabel"
          data-cy="UserHistoryCreateUpdateHeading"
          v-text="$t('xmplVueSpringwiseApp.userHistory.home.createOrEditLabel')"
        >
          Create or edit a UserHistory
        </h2>
        <div>
          <div class="form-group" v-if="userHistory.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="userHistory.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('xmplVueSpringwiseApp.userHistory.name')" for="user-history-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="user-history-name"
              data-cy="name"
              :class="{ valid: !$v.userHistory.name.$invalid, invalid: $v.userHistory.name.$invalid }"
              v-model="$v.userHistory.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('xmplVueSpringwiseApp.userHistory.issue')" for="user-history-issue">Issue</label>
            <input
              type="text"
              class="form-control"
              name="issue"
              id="user-history-issue"
              data-cy="issue"
              :class="{ valid: !$v.userHistory.issue.$invalid, invalid: $v.userHistory.issue.$invalid }"
              v-model="$v.userHistory.issue.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('xmplVueSpringwiseApp.userHistory.issueDate')" for="user-history-issueDate"
              >Issue Date</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="user-history-issueDate"
                  v-model="$v.userHistory.issueDate.$model"
                  name="issueDate"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="user-history-issueDate"
                data-cy="issueDate"
                type="text"
                class="form-control"
                name="issueDate"
                :class="{ valid: !$v.userHistory.issueDate.$invalid, invalid: $v.userHistory.issueDate.$invalid }"
                v-model="$v.userHistory.issueDate.$model"
              />
            </b-input-group>
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
            :disabled="$v.userHistory.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./user-history-update.component.ts"></script>
