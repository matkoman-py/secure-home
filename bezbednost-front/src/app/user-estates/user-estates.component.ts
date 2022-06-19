import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MessageService } from 'primeng/api';
import { Estate } from '../model/estate';
import { UserEstatesService } from './services/user-estates.service';

@Component({
  selector: 'app-user-estates',
  templateUrl: './user-estates.component.html',
  styleUrls: ['./user-estates.component.css'],
  providers: [MessageService]
})
export class UserEstatesComponent implements OnInit {

  constructor(private route: ActivatedRoute, private userEstatesService: UserEstatesService, private messageService: MessageService) { }

  userEstates: Estate[] = []
  allEstates: Estate[] = []
  userId: number = parseInt(this.route.snapshot.paramMap.get('id')!)
  selectedEstate: Estate = {
    id: 0,
    address:"",
    description:"",
    estateType:""
  }

  getAllEstates(): void {
    this.userEstatesService.getAllEstates().subscribe(res => {
      this.allEstates = res;
    })
  }

  getEstatesForUser(): void {
    this.userEstatesService.getEstatesForUser(this.userId).subscribe(res =>{
      this.userEstates = res;
    })
  }
  ngOnInit(): void {
    this.getEstatesForUser();
    this.getAllEstates();
    
  }

  addEstate(): void {
    if(!this.selectedEstate) {
      this.messageService.add({
        key: 'tc',
        severity: 'error',
        summary: 'Warning',
        detail: 'No estate has been selected!',
      });
      return;
    }
    if(this.selectedEstate.id == 0) {
      this.messageService.add({
        key: 'tc',
        severity: 'error',
        summary: 'Warning',
        detail: 'No estate has been selected!',
      });
      return;
    }
    
    this.userEstatesService.addEstateToUser(this.userId, this.selectedEstate.id).subscribe(res => {
      this.messageService.add({
        key: 'tc',
        severity: 'success',
        summary: 'Success',
        detail: 'Estate successfully added!',
      });
      this.getEstatesForUser();
    })
  }




}
