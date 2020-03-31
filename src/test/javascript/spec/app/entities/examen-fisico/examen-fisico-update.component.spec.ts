import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PracticaTestModule } from '../../../test.module';
import { ExamenFisicoUpdateComponent } from 'app/entities/examen-fisico/examen-fisico-update.component';
import { ExamenFisicoService } from 'app/entities/examen-fisico/examen-fisico.service';
import { ExamenFisico } from 'app/shared/model/examen-fisico.model';

describe('Component Tests', () => {
  describe('ExamenFisico Management Update Component', () => {
    let comp: ExamenFisicoUpdateComponent;
    let fixture: ComponentFixture<ExamenFisicoUpdateComponent>;
    let service: ExamenFisicoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PracticaTestModule],
        declarations: [ExamenFisicoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ExamenFisicoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ExamenFisicoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ExamenFisicoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ExamenFisico(123);
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
        const entity = new ExamenFisico();
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
