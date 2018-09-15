import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { InvalidationRule } from 'app/shared/model/invalidation-rule.model';
import { InvalidationRuleService } from './invalidation-rule.service';
import { InvalidationRuleComponent } from './invalidation-rule.component';
import { InvalidationRuleDetailComponent } from './invalidation-rule-detail.component';
import { InvalidationRuleUpdateComponent } from './invalidation-rule-update.component';
import { InvalidationRuleDeletePopupComponent } from './invalidation-rule-delete-dialog.component';
import { IInvalidationRule } from 'app/shared/model/invalidation-rule.model';

@Injectable({ providedIn: 'root' })
export class InvalidationRuleResolve implements Resolve<IInvalidationRule> {
    constructor(private service: InvalidationRuleService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((invalidationRule: HttpResponse<InvalidationRule>) => invalidationRule.body));
        }
        return of(new InvalidationRule());
    }
}

export const invalidationRuleRoute: Routes = [
    {
        path: 'invalidation-rule',
        component: InvalidationRuleComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InvalidationRules'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'invalidation-rule/:id/view',
        component: InvalidationRuleDetailComponent,
        resolve: {
            invalidationRule: InvalidationRuleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InvalidationRules'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'invalidation-rule/new',
        component: InvalidationRuleUpdateComponent,
        resolve: {
            invalidationRule: InvalidationRuleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InvalidationRules'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'invalidation-rule/:id/edit',
        component: InvalidationRuleUpdateComponent,
        resolve: {
            invalidationRule: InvalidationRuleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InvalidationRules'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const invalidationRulePopupRoute: Routes = [
    {
        path: 'invalidation-rule/:id/delete',
        component: InvalidationRuleDeletePopupComponent,
        resolve: {
            invalidationRule: InvalidationRuleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InvalidationRules'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
