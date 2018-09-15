import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { UploadedFile } from 'app/shared/model/uploaded-file.model';
import { UploadedFileService } from './uploaded-file.service';
import { UploadedFileComponent } from './uploaded-file.component';
import { UploadedFileDetailComponent } from './uploaded-file-detail.component';
import { UploadedFileUpdateComponent } from './uploaded-file-update.component';
import { UploadedFileDeletePopupComponent } from './uploaded-file-delete-dialog.component';
import { IUploadedFile } from 'app/shared/model/uploaded-file.model';

@Injectable({ providedIn: 'root' })
export class UploadedFileResolve implements Resolve<IUploadedFile> {
    constructor(private service: UploadedFileService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((uploadedFile: HttpResponse<UploadedFile>) => uploadedFile.body));
        }
        return of(new UploadedFile());
    }
}

export const uploadedFileRoute: Routes = [
    {
        path: 'uploaded-file',
        component: UploadedFileComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UploadedFiles'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'uploaded-file/:id/view',
        component: UploadedFileDetailComponent,
        resolve: {
            uploadedFile: UploadedFileResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UploadedFiles'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'uploaded-file/new',
        component: UploadedFileUpdateComponent,
        resolve: {
            uploadedFile: UploadedFileResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UploadedFiles'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'uploaded-file/:id/edit',
        component: UploadedFileUpdateComponent,
        resolve: {
            uploadedFile: UploadedFileResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UploadedFiles'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const uploadedFilePopupRoute: Routes = [
    {
        path: 'uploaded-file/:id/delete',
        component: UploadedFileDeletePopupComponent,
        resolve: {
            uploadedFile: UploadedFileResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UploadedFiles'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
