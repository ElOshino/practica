import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'doctor',
        loadChildren: () => import('./doctor/doctor.module').then(m => m.PracticaDoctorModule)
      },
      {
        path: 'paciente',
        loadChildren: () => import('./paciente/paciente.module').then(m => m.PracticaPacienteModule)
      },
      {
        path: 'antecedentes-familiares',
        loadChildren: () =>
          import('./antecedentes-familiares/antecedentes-familiares.module').then(m => m.PracticaAntecedentesFamiliaresModule)
      },
      {
        path: 'antecedentes-personales',
        loadChildren: () =>
          import('./antecedentes-personales/antecedentes-personales.module').then(m => m.PracticaAntecedentesPersonalesModule)
      },
      {
        path: 'consulta',
        loadChildren: () => import('./consulta/consulta.module').then(m => m.PracticaConsultaModule)
      },
      {
        path: 'medicamentos',
        loadChildren: () => import('./medicamentos/medicamentos.module').then(m => m.PracticaMedicamentosModule)
      },
      {
        path: 'examen-fisico',
        loadChildren: () => import('./examen-fisico/examen-fisico.module').then(m => m.PracticaExamenFisicoModule)
      },
      {
        path: 'signos-vitales',
        loadChildren: () => import('./signos-vitales/signos-vitales.module').then(m => m.PracticaSignosVitalesModule)
      },
      {
        path: 'historia-usuario',
        loadChildren: () => import('./historia-usuario/historia-usuario.module').then(m => m.PracticaHistoriaUsuarioModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class PracticaEntityModule {}
