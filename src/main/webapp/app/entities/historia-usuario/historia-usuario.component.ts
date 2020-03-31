import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IHistoriaUsuario } from 'app/shared/model/historia-usuario.model';
import { HistoriaUsuarioService } from './historia-usuario.service';
import { HistoriaUsuarioDeleteDialogComponent } from './historia-usuario-delete-dialog.component';

@Component({
  selector: 'jhi-historia-usuario',
  templateUrl: './historia-usuario.component.html'
})
export class HistoriaUsuarioComponent implements OnInit, OnDestroy {
  historiaUsuarios?: IHistoriaUsuario[];
  eventSubscriber?: Subscription;

  constructor(
    protected historiaUsuarioService: HistoriaUsuarioService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.historiaUsuarioService.query().subscribe((res: HttpResponse<IHistoriaUsuario[]>) => (this.historiaUsuarios = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInHistoriaUsuarios();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IHistoriaUsuario): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInHistoriaUsuarios(): void {
    this.eventSubscriber = this.eventManager.subscribe('historiaUsuarioListModification', () => this.loadAll());
  }

  delete(historiaUsuario: IHistoriaUsuario): void {
    const modalRef = this.modalService.open(HistoriaUsuarioDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.historiaUsuario = historiaUsuario;
  }
}
