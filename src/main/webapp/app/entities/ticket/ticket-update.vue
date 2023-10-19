<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="xmplVueSpringwiseApp.ticket.home.createOrEditLabel"
          data-cy="TicketCreateUpdateHeading"
          v-text="$t('xmplVueSpringwiseApp.ticket.home.createOrEditLabel')"
        >
          Create or edit a Ticket
        </h2>
        <div>
          <div class="form-group" v-if="ticket.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="ticket.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('xmplVueSpringwiseApp.ticket.issue')" for="ticket-issue">Issue</label>
            <input
              type="text"
              class="form-control"
              name="issue"
              id="ticket-issue"
              data-cy="issue"
              :class="{ valid: !$v.ticket.issue.$invalid, invalid: $v.ticket.issue.$invalid }"
              v-model="$v.ticket.issue.$model"
              required
            />
            <div v-if="$v.ticket.issue.$anyDirty && $v.ticket.issue.$invalid">
              <small class="form-text text-danger" v-if="!$v.ticket.issue.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('xmplVueSpringwiseApp.ticket.timeStamp')" for="ticket-timeStamp">Time Stamp</label>
            <div class="d-flex">
              <input
                id="ticket-timeStamp"
                data-cy="timeStamp"
                type="datetime-local"
                class="form-control"
                name="timeStamp"
                :class="{ valid: !$v.ticket.timeStamp.$invalid, invalid: $v.ticket.timeStamp.$invalid }"
                :value="convertDateTimeFromServer($v.ticket.timeStamp.$model)"
                @change="updateInstantField('timeStamp', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('xmplVueSpringwiseApp.ticket.student')" for="ticket-student">Student</label>
            <select class="form-control" id="ticket-student" data-cy="student" name="student" v-model="ticket.student">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="ticket.student && studentOption.id === ticket.student.id ? ticket.student : studentOption"
                v-for="studentOption in students"
                :key="studentOption.id"
              >
                {{ studentOption.id }}
              </option>
            </select>
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
            :disabled="$v.ticket.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./ticket-update.component.ts"></script>
