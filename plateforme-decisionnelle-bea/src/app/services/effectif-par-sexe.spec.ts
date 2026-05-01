import { TestBed } from '@angular/core/testing';

import { EffectifParSexe } from './effectif-par-sexe';

describe('EffectifParSexe', () => {
  let service: EffectifParSexe;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EffectifParSexe);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
