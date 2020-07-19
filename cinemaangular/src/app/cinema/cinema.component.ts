import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {CinemaService} from "../services/cinema.service";

@Component({
  selector: 'app-cinema',
  templateUrl: './cinema.component.html',
  styleUrls: ['./cinema.component.css']
})
export class CinemaComponent implements OnInit {
  public villes; public cinemas; public salles;
  public currentVille;
  public currentCinema;
  public currentProjection;
  constructor(public cinemaService:CinemaService) { }

  ngOnInit(){
    this.cinemaService.getVilles()
      .subscribe(data=>
      {this.villes=data;
      },err=>{
      console.log(err);
      });
  }

  onGetCinema(v) {
    this.currentVille=v;
    this.cinemaService.getCinemas(v)
      .subscribe(data=>
      {this.cinemas=data;
      },err=>{
        console.log(err);
      });
  }

  onGetSalle(c) {
    this.currentCinema=c;
    this.cinemaService.getSalles(c)
      .subscribe(data=>
      {this.salles=data;
      this.salles._embedded.salles.forEach(salle=>{
        this.cinemaService.getProjections(salle)
          .subscribe(data=>
          {salle.projections=data;
          },err=>{
            console.log(err);
          });
      })
      },err=>{
        console.log(err);
      });
  }

  onGetTicketsPlace(p) {
    this.currentProjection=p;
    this.cinemaService.getTicketsPlaces(p)
      .subscribe(data=>
      {this.currentProjection.tickets=data;
      },err=>{
        console.log(err);
      })
  }
}
