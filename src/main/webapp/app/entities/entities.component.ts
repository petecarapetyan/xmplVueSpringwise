import { Component, Provide, Vue } from 'vue-property-decorator';

import UserService from '@/entities/user/user.service';
import StudentService from './student/student.service';
import AirplaneService from './airplane/airplane.service';
import CarService from './car/car.service';
import TruckService from './truck/truck.service';
import CodingCategoryService from './coding-category/coding-category.service';
import DogService from './dog/dog.service';
import FrogService from './frog/frog.service';
import MovieService from './movie/movie.service';
import SpringProjectService from './spring-project/spring-project.service';
import UserHistoryService from './user-history/user-history.service';
import TicketService from './ticket/ticket.service';
import ScoreTypeService from './score-type/score-type.service';
import ScoreService from './score/score.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

@Component
export default class Entities extends Vue {
  @Provide('userService') private userService = () => new UserService();
  @Provide('studentService') private studentService = () => new StudentService();
  @Provide('airplaneService') private airplaneService = () => new AirplaneService();
  @Provide('carService') private carService = () => new CarService();
  @Provide('truckService') private truckService = () => new TruckService();
  @Provide('codingCategoryService') private codingCategoryService = () => new CodingCategoryService();
  @Provide('dogService') private dogService = () => new DogService();
  @Provide('frogService') private frogService = () => new FrogService();
  @Provide('movieService') private movieService = () => new MovieService();
  @Provide('springProjectService') private springProjectService = () => new SpringProjectService();
  @Provide('userHistoryService') private userHistoryService = () => new UserHistoryService();
  @Provide('ticketService') private ticketService = () => new TicketService();
  @Provide('scoreTypeService') private scoreTypeService = () => new ScoreTypeService();
  @Provide('scoreService') private scoreService = () => new ScoreService();
  // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
}
