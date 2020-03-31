import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PracticaTestModule } from '../../../test.module';
import { MedicamentosUpdateComponent } from 'app/entities/medicamentos/medicamentos-update.component';
import { MedicamentosService } from 'app/entities/medicamentos/medicamentos.service';
import { Medicamentos } from 'app/shared/model/medicamentos.model';

describe('Component Tests', () => {
  describe('Medicamentos Management Update Component', () => {
    let comp: MedicamentosUpdateComponent;
    let fixture: ComponentFixture<MedicamentosUpdateComponent>;
    let service: MedicamentosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PracticaTestModule],
        declarations: [MedicamentosUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MedicamentosUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MedicamentosUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MedicamentosService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Medicamentos(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Medicamentos();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
