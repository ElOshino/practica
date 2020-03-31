import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAntecedentesFamiliares } from 'app/shared/model/antecedentes-familiares.model';
import { AntecedentesFamiliaresService } from './antecedentes-familiares.service';
import { AntecedentesFamiliaresDeleteDialogComponent } from './antecedentes-familiares-delete-dialog.component';

@Component({
  selector: 'jhi-antecedentes-familiares',
  templateUrl: './antecedentes-familiares.component.html'
})
export class AntecedentesFamiliaresComponent implements OnInit, OnDestroy {
  antecedentesFamiliares?: IAntecedentesFamiliares[];
  eventSubscriber?: Subscription;

  constructor(
    protected antecedentesFamiliaresService: AntecedentesFamiliaresService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.antecedentesFamiliaresService
      .query()
      .subscribe((res: HttpResponse<IAntecedentesFamiliares[]>) => (this.antecedentesFamiliares = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAntecedentesFamiliares();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAntecedentesFamiliares): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAntecedentesFamiliares(): void {
    this.eventSubscriber = this.eventManager.subscribe('antecedentesFamiliaresListModification', () => this.loadAll());
  }

  delete(antecedentesFamiliares: IAntecedentesFamiliares): void {
    const modalRef = this.modalService.open(AntecedentesFamiliaresDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.antecedentesFamiliares = antecedentesFamiliares;
  }
}
