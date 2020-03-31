import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PracticaTestModule } from '../../../test.module';
import { MedicamentosComponent } from 'app/entities/medicamentos/medicamentos.component';
import { MedicamentosService } from 'app/entities/medicamentos/medicamentos.service';
import { Medicamentos } from 'app/shared/model/medicamentos.model';

describe('Component Tests', () => {
  describe('Medicamentos Management Component', () => {
    let comp: MedicamentosComponent;
    let fixture: ComponentFixture<MedicamentosComponent>;
    let service: MedicamentosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PracticaTestModule],
        declarations: [MedicamentosComponent]
      })
        .overrideTemplate(MedicamentosComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MedicamentosComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MedicamentosService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Medicamentos(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.medicamentos && comp.medicamentos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
