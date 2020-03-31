import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PracticaTestModule } from '../../../test.module';
import { ExamenFisicoDetailComponent } from 'app/entities/examen-fisico/examen-fisico-detail.component';
import { ExamenFisico } from 'app/shared/model/examen-fisico.model';

describe('Component Tests', () => {
  describe('ExamenFisico Management Detail Component', () => {
    let comp: ExamenFisicoDetailComponent;
    let fixture: ComponentFixture<ExamenFisicoDetailComponent>;
    const route = ({ data: of({ examenFisico: new ExamenFisico(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PracticaTestModule],
        declarations: [ExamenFisicoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ExamenFisicoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ExamenFisicoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load examenFisico on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.examenFisico).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
