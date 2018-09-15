import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { InvalidationCode } from 'app/shared/model/invalidation-code.model';
import { InvalidationCodeService } from './invalidation-code.service';
import { InvalidationCodeComponent } from './invalidation-code.component';
import { InvalidationCodeDetailComponent } from './invalidation-code-detail.component';
import { InvalidationCodeUpdateComponent } from './invalidation-code-update.component';
import { InvalidationCodeDeletePopupComponent } from './invalidation-code-delete-dialog.component';
import { IInvalidationCode } from 'app/shared/model/invalidation-code.model';

@Injectable({ providedIn: 'root' })
export class InvalidationCodeResolve implements Resolve<IInvalidationCode> {
    constructor(private service: InvalidationCodeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((invalidationCode: HttpResponse<InvalidationCode>) => invalidationCode.body));
        }
        return of(new InvalidationCode());
    }
}

export const invalidationCodeRoute: Routes = [
    {
        path: 'invalidation-code',
        component: InvalidationCodeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InvalidationCodes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'invalidation-code/:id/view',
        component: InvalidationCodeDetailComponent,
        resolve: {
            invalidationCode: InvalidationCodeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InvalidationCodes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'invalidation-code/new',
        component: InvalidationCodeUpdateComponent,
        resolve: {
            invalidationCode: InvalidationCodeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InvalidationCodes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'invalidation-code/:id/edit',
        component: InvalidationCodeUpdateComponent,
        resolve: {
            invalidationCode: InvalidationCodeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InvalidationCodes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const invalidationCodePopupRoute: Routes = [
    {
        path: 'invalidation-code/:id/delete',
        component: InvalidationCodeDeletePopupComponent,
        resolve: {
            invalidationCode: InvalidationCodeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InvalidationCodes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
