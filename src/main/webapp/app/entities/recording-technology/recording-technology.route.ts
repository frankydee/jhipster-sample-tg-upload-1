import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { RecordingTechnology } from 'app/shared/model/recording-technology.model';
import { RecordingTechnologyService } from './recording-technology.service';
import { RecordingTechnologyComponent } from './recording-technology.component';
import { RecordingTechnologyDetailComponent } from './recording-technology-detail.component';
import { RecordingTechnologyUpdateComponent } from './recording-technology-update.component';
import { RecordingTechnologyDeletePopupComponent } from './recording-technology-delete-dialog.component';
import { IRecordingTechnology } from 'app/shared/model/recording-technology.model';

@Injectable({ providedIn: 'root' })
export class RecordingTechnologyResolve implements Resolve<IRecordingTechnology> {
    constructor(private service: RecordingTechnologyService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((recordingTechnology: HttpResponse<RecordingTechnology>) => recordingTechnology.body));
        }
        return of(new RecordingTechnology());
    }
}

export const recordingTechnologyRoute: Routes = [
    {
        path: 'recording-technology',
        component: RecordingTechnologyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RecordingTechnologies'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'recording-technology/:id/view',
        component: RecordingTechnologyDetailComponent,
        resolve: {
            recordingTechnology: RecordingTechnologyResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RecordingTechnologies'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'recording-technology/new',
        component: RecordingTechnologyUpdateComponent,
        resolve: {
            recordingTechnology: RecordingTechnologyResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RecordingTechnologies'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'recording-technology/:id/edit',
        component: RecordingTechnologyUpdateComponent,
        resolve: {
            recordingTechnology: RecordingTechnologyResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RecordingTechnologies'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const recordingTechnologyPopupRoute: Routes = [
    {
        path: 'recording-technology/:id/delete',
        component: RecordingTechnologyDeletePopupComponent,
        resolve: {
            recordingTechnology: RecordingTechnologyResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RecordingTechnologies'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
