import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISignosVitales } from 'app/shared/model/signos-vitales.model';
import { SignosVitalesService } from './signos-vitales.service';
import { SignosVitalesDeleteDialogComponent } from './signos-vitales-delete-dialog.component';

@Component({
  selector: 'jhi-signos-vitales',
  templateUrl: './signos-vitales.component.html'
})
export class SignosVitalesComponent implements OnInit, OnDestroy {
  signosVitales?: ISignosVitales[];
  eventSubscriber?: Subscription;

  constructor(
    protected signosVitalesService: SignosVitalesService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.signosVitalesService.query().subscribe((res: HttpResponse<ISignosVitales[]>) => (this.signosVitales = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSignosVitales();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISignosVitales): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSignosVitales(): void {
    this.eventSubscriber = this.eventManager.subscribe('signosVitalesListModification', () => this.loadAll());
  }

  delete(signosVitales: ISignosVitales): void {
    const modalRef = this.modalService.open(SignosVitalesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.signosVitales = signosVitales;
  }
}
