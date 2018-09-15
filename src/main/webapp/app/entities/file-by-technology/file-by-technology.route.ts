import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { FileByTechnology } from 'app/shared/model/file-by-technology.model';
import { FileByTechnologyService } from './file-by-technology.service';
import { FileByTechnologyComponent } from './file-by-technology.component';
import { FileByTechnologyDetailComponent } from './file-by-technology-detail.component';
import { FileByTechnologyUpdateComponent } from './file-by-technology-update.component';
import { FileByTechnologyDeletePopupComponent } from './file-by-technology-delete-dialog.component';
import { IFileByTechnology } from 'app/shared/model/file-by-technology.model';

@Injectable({ providedIn: 'root' })
export class FileByTechnologyResolve implements Resolve<IFileByTechnology> {
    constructor(private service: FileByTechnologyService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((fileByTechnology: HttpResponse<FileByTechnology>) => fileByTechnology.body));
        }
        return of(new FileByTechnology());
    }
}

export const fileByTechnologyRoute: Routes = [
    {
        path: 'file-by-technology',
        component: FileByTechnologyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FileByTechnologies'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'file-by-technology/:id/view',
        component: FileByTechnologyDetailComponent,
        resolve: {
            fileByTechnology: FileByTechnologyResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FileByTechnologies'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'file-by-technology/new',
        component: FileByTechnologyUpdateComponent,
        resolve: {
            fileByTechnology: FileByTechnologyResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FileByTechnologies'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'file-by-technology/:id/edit',
        component: FileByTechnologyUpdateComponent,
        resolve: {
            fileByTechnology: FileByTechnologyResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FileByTechnologies'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const fileByTechnologyPopupRoute: Routes = [
    {
        path: 'file-by-technology/:id/delete',
        component: FileByTechnologyDeletePopupComponent,
        resolve: {
            fileByTechnology: FileByTechnologyResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FileByTechnologies'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
