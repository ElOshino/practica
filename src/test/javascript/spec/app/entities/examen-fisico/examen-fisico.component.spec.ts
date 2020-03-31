import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PracticaTestModule } from '../../../test.module';
import { ExamenFisicoComponent } from 'app/entities/examen-fisico/examen-fisico.component';
import { ExamenFisicoService } from 'app/entities/examen-fisico/examen-fisico.service';
import { ExamenFisico } from 'app/shared/model/examen-fisico.model';

describe('Component Tests', () => {
  describe('ExamenFisico Management Component', () => {
    let comp: ExamenFisicoComponent;
    let fixture: ComponentFixture<ExamenFisicoComponent>;
    let service: ExamenFisicoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PracticaTestModule],
        declarations: [ExamenFisicoComponent]
      })
        .overrideTemplate(ExamenFisicoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ExamenFisicoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ExamenFisicoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ExamenFisico(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.examenFisicos && comp.examenFisicos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
