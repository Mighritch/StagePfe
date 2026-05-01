import { TestBed } from '@angular/core/testing';

import { EffectifParAffectation } from './effectif-par-affectation';

describe('EffectifParAffectation', () => {
  let service: EffectifParAffectation;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EffectifParAffectation);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
